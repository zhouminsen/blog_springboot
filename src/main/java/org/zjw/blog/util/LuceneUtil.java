package org.zjw.blog.util;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class LuceneUtil {

	private static Directory directory;

	private static IndexWriterConfig indexWriterConfig;

	public LuceneUtil(String path, Analyzer analyzer) {
		try {
			directory = FSDirectory.open(Paths.get(path));
			getIndexWriterConfig(analyzer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	};

	/**
	 * 获得getIndexWriterConfig
	 * 
	 * @param analyzer
	 *            分词器
	 * @return
	 */
	private IndexWriterConfig getIndexWriterConfig(Analyzer analyzer) {
		indexWriterConfig = new IndexWriterConfig(analyzer);
		return indexWriterConfig;
	}

	/**
	 * 获得写入器
	 * 
	 * @return
	 * @throws IOException
	 */
	public IndexWriter getIndexWriter() throws IOException {
		return new IndexWriter(directory, indexWriterConfig);
	}

	/**
	 * 获得搜索器
	 * 
	 * @return
	 * @throws IOException
	 */
	public IndexSearcher getIndexSearcher() throws IOException {
		IndexReader ir = DirectoryReader.open(directory);
		IndexSearcher is = new IndexSearcher(ir);
		return is;
	}

	/**
	 * 得到query
	 * 
	 * @param field
	 *            搜索的字段名称
	 * @param keyWord
	 *            要搜索的关键字
	 * @param analyzer
	 *            分词器
	 * @return
	 * @throws ParseException
	 */
	public Query getQuery(String field, String keyWord, Analyzer analyzer)
			throws ParseException {
		// 添加要搜索的目录名
		QueryParser queryParser = new QueryParser(field, analyzer);
		// 解析要搜索的关键字
		Query query = queryParser.parse(keyWord);
		return query;
	}

	/**
	 * 得到高点显示器
	 * 
	 * @param startFormatter
	 *            开始格式化标签
	 * @param endFormatter
	 *            结束可视化标签
	 * @param scorer
	 *            可以放入搜索query的子类
	 * @return
	 */
	public Highlighter getHighlighter(String startFormatter,
			String endFormatter, Scorer scorer) {
		Formatter formatter = new SimpleHTMLFormatter(startFormatter,
				endFormatter);
		Highlighter highlighter = new Highlighter(formatter, scorer);
		return highlighter;
	}

	/**
	 * 获得高亮的文本
	 * @param analyzer 分词器
	 * @param fieldName 字段名称
	 * @param targetText 目标文本
	 * @param highlighter 高亮显示器
	 * @return
	 * @throws IOException
	 * @throws InvalidTokenOffsetsException
	 */
	public String getHighText(Analyzer analyzer, String fieldName,
			String targetText, Highlighter highlighter) throws IOException, InvalidTokenOffsetsException {
		Reader reader = new StringReader(targetText);
		TokenStream tokenStream = analyzer.tokenStream(fieldName, reader);
		String highText = highlighter.getBestFragment(tokenStream,targetText);
		return highText;
	}

}
