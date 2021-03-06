package org.sam.shen.scheduing.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.sam.shen.scheduing.entity.JobInfo;

import com.github.pagehelper.Page;
import org.sam.shen.scheduing.vo.JobApiVo;

@Mapper
public interface JobInfoMapper {

	void saveJobInfo(JobInfo jobInfo);

	void batchInsert(List<JobInfo> list);
	
	JobInfo findJobInfoById(Long id);
	
	Page<JobInfo> queryJobInfoForPager(@Param("jobName") String jobName, @Param("userId") Long userId);
	
	List<JobInfo> queryJobInfoForList(@Param("jobName") String jobName, @Param("userId") Long userId);
	
	List<JobInfo> queryJobInfoInIds(@Param("list") List<Long> ids, @Param("userId") Long userId);
	
	void upgradeJonInfo(JobInfo jobInfo);
	
	List<JobInfo> queryJobInfoByEnable(@Param("enable") int enable, @Param("userId") Long userId);
	
	// ---------------  统计  ------------------------
	
	Integer countJobInfoByEnable(@Param("enable") int enable, @Param("userId") Long userId);

	List<JobInfo> findJobInfoByParentId(String id);

    /**
     * 用于查询启动时待加载的job
     * @return 待加载的job
     */
	List<JobInfo> queryLoadedJobs();

	JobApiVo findJobAppById(Long jobId);

	List<JobInfo> findJobsByAppId(String appId);

	int deleteJobById(long jobId);

}
