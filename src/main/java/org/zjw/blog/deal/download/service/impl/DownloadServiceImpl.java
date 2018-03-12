package org.zjw.blog.deal.download.service.impl;

import org.zjw.blog.deal.download.dao.FileRecordMapper;
import org.zjw.blog.deal.download.entity.FileRecord;
import org.zjw.blog.deal.download.service.DownloadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by zhoum on 2017/1/22.
 */
@Service("downloadService")
public class DownloadServiceImpl implements DownloadService {

    @Resource
    private FileRecordMapper fileRecordMapper;

    /**
     * 查看文件记录
     *
     * @param paramMap
     * @return
     */
    @Override
    public List<FileRecord> list(Map<String, Object> paramMap) {
        List<FileRecord> fileRecordList=fileRecordMapper.getFileRecordList(paramMap);
        return fileRecordList;
    }
}

