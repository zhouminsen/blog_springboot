package org.zjw.blog.base.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.zjw.blog.base.vo.blog.BlogLuceneVo;
import org.zjw.blog.deal.blog.entity.Blog;
import org.zjw.blog.util.DateUtil;
import org.zjw.blog.util.LuceneUtil;
import org.zjw.blog.util.UtilFuns;
import org.springframework.stereotype.Component;

/**
 * lucene博客工具类
 * @author Administrator
 * @date 2016-7-10
 */
@Component
public class BlogLucene {
	private static String dirpath = null;
	static {
		dirpath =  "D:/blog/luceneFile";
	}

	/**
	 * 添加锁引
	 * 
	 * @param blog
	 * @throws IOException
	 */
	public void addIndex(Blog blog) throws IOException {
		Document doc = convertDocument(blog);
		LuceneUtil luceneUtil = new LuceneUtil(dirpath,
				new SmartChineseAnalyzer());
		IndexWriter indexWriter = luceneUtil.getIndexWriter();
		indexWriter.addDocument(doc);
		indexWriter.close();
	}

	/**
	 * 更新索引
	 * 
	 * @param blog
	 * @throws IOException
	 */
	public void updateIndex(Blog blog) throws IOException {
		Document doc = convertDocument(blog);
		LuceneUtil luceneUtil = new LuceneUtil(dirpath,
				new SmartChineseAnalyzer());
		IndexWriter indexWriter = luceneUtil.getIndexWriter();
		indexWriter.updateDocument(new Term("id", blog.getId().toString()),
				doc);
		indexWriter.close();
	}

	public void deleteIndex(Blog blog) throws IOException {
		LuceneUtil luceneUtil = new LuceneUtil(dirpath,
				new SmartChineseAnalyzer());
		IndexWriter indexWriter = luceneUtil.getIndexWriter();
		//删除时   id一定要和document的field数值类型对应
		indexWriter.deleteDocuments(new Term("id", blog.getId().toString()));
		indexWriter.forceMergeDeletes();
		indexWriter.commit();
		indexWriter.close();
	}

	/**
	 * 查旭索引
	 * 
	 * @throws IOException
	 * @throws ParseException
	 * @throws InvalidTokenOffsetsException
	 */
	public List<BlogLuceneVo> getIndexSearcher(String keyword, int start,
                                               int rows) throws IOException, ParseException,
			InvalidTokenOffsetsException {
		// 中文分词器
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
		LuceneUtil luceneUtil = new LuceneUtil(dirpath, analyzer);
		IndexSearcher is = luceneUtil.getIndexSearcher();
		BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
		// 添加查询条件
		booleanQuery.add(luceneUtil.getQuery("title", keyword, analyzer),
				Occur.SHOULD);
		booleanQuery.add(luceneUtil.getQuery("content", keyword, analyzer),
				Occur.SHOULD);
		// 按id排序
		SortField sortField = new SortField("sortId", Type.INT,true);
		Sort sort = new Sort(sortField);
		// 要查询的最大数量
		TopDocs topDocs = is.search(booleanQuery.build(), start + rows, sort);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		// 得到最小的值 循环下标结束
		int end = Math.min(scoreDocs.length, start + rows);
		List<BlogLuceneVo> tBlogLuceneVoList = new ArrayList<BlogLuceneVo>();
		Scorer scorer = new QueryScorer(booleanQuery.build());
		Highlighter highlighter = luceneUtil.getHighlighter(
				"<b><font color='red'>", "</font></b>", scorer);
		for (int i = start; i < end; i++) {
			BlogLuceneVo blv = new BlogLuceneVo();
			Document doc = is.doc(scoreDocs[i].doc);
			String content = doc.get("content");
			String title = doc.get("title");
			blv.setSummary(doc.get("summary"));
			blv.setId(doc.get("id"));
			blv.setReleaseDate(doc.get("releaseDate"));
			if (UtilFuns.isNotEmpty(title)) {
				String highText = highlighter.getBestFragment(analyzer,"title", title);
				if (UtilFuns.isNotEmpty(highText)) {
					blv.setTitle(highText);
				} else {
					blv.setTitle(title);
				}
			}
			if (UtilFuns.isNotEmpty(content)) {
				String highText = luceneUtil.getHighText(analyzer, "content",content, highlighter);
				if (UtilFuns.isNotEmpty(highText)) {
					blv.setContent(highText);
				} else {
					blv.setContent(content);
				}
			}
			tBlogLuceneVoList.add(blv);
		}
		return tBlogLuceneVoList;
	}

	/**
	 * 根据关键字查询总记录数
	 * 
	 * @param keyword
	 *            关键字
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public int getCountByCondition(String keyword) throws IOException,
			ParseException {
		// 中文分词器
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
		LuceneUtil luceneUtil = new LuceneUtil(dirpath, analyzer);
		IndexSearcher is = luceneUtil.getIndexSearcher();
		BooleanQuery booleanQuery = new BooleanQuery();
		// 添加查询条件
		booleanQuery.add(luceneUtil.getQuery("title", keyword, analyzer),
				Occur.SHOULD);
		booleanQuery.add(luceneUtil.getQuery("content", keyword, analyzer),
				Occur.SHOULD);
		// 按id排序
		TopDocs topDocs = is.search(booleanQuery, Integer.MAX_VALUE);
		int totalCount = topDocs.scoreDocs.length;
		return totalCount;

	}

	/**
	 * blog实体转换为document
	 * 
	 * @param blog
	 * @return
	 */
	public Document convertDocument(Blog blog) {

		Document doc = new Document();
		// 如果根据id搜索排序的话在lucene5.0版本前可以用
		// doc.add(new IntField("id", tBlog.getId(), Field.Store.YES));

		// 如果根据id搜索排序的话lucene5.0版本之后可以用
		doc.add(new NumericDocValuesField("sortId", blog.getId()));
		
		doc.add(new StringField("id", blog.getId().toString(), Store.YES));
		doc.add(new TextField("title", blog.getTitle(), Store.YES));
		doc.add(new TextField("content", blog.getContent(), Store.YES));
		doc.add(new TextField("releaseDate", DateUtil.dateToStr(blog.getReleaseDate()), Store.YES));
		doc.add(new StringField("summary", blog.getSummary(), Store.YES));
		return doc;
	}
}
