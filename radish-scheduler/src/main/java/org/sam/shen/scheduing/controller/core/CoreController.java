package org.sam.shen.scheduing.controller.core;

import org.sam.shen.core.event.HandlerEvent;
import org.sam.shen.core.model.AgentInfo;
import org.sam.shen.core.model.AgentPerformance;
import org.sam.shen.core.model.Resp;
import org.sam.shen.scheduing.entity.JobEvent;
import org.sam.shen.scheduing.service.AgentService;
import org.sam.shen.scheduing.service.JobEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author suoyao
 * @date 2018年7月31日 下午3:23:54 Agent core APIs
 */
@RestController
@RequestMapping(value = "/core")
public class CoreController {
	Logger logger = LoggerFactory.getLogger(CoreController.class);

	@Autowired
	private AgentService agentService;
	
	@Autowired
	private JobEventService jobEventService;

	/**
	 * @author suoyao
	 * @date 下午12:58:09
	 * @param agentInfo
	 * @return Agent Registry
	 */
	@RequestMapping(value = "/registry", method = RequestMethod.PUT)
	public Resp<Long> registry(@RequestBody AgentInfo agentInfo) {
		if (logger.isInfoEnabled()) {
			logger.info("Agent Registry : {}", agentInfo.toString());
		}
		Long agentId = agentService.registry(agentInfo);
		if (agentId > 0) {
			return new Resp<Long>(agentId);
		}
		return new Resp<Long>(0, "Resigtry Fail", -1L);
	}

	/**
	 * @author suoyao
	 * @date 下午3:24:44
	 * @return Agent heartbeat call
	 */
	@RequestMapping(value = "/heartbeat", method = RequestMethod.POST)
	public Resp<AgentPerformance> heartbeat(@RequestBody AgentPerformance agent) {
		logger.info("agent {} heartbeat: {}", agent.getAgentName(), agent.toString());
		return new Resp<>(agent);
	}

	/**
	 * @author suoyao
	 * @date 下午5:57:48
	 * @param agentName
	 * @return 触发任务接口
	 */
	@RequestMapping(value = "/trigger-event/{agentId}", method = RequestMethod.GET)
	public Resp<HandlerEvent> triggerEvent(@PathVariable(value = "agentId", required = false) Long agentId) {
		// 根据Agent机器性能决定是否能抢到任务
		JobEvent jobEvent = jobEventService.triggerJobEvent(agentId);
		if(null != jobEvent) {
			HandlerEvent event = new HandlerEvent(jobEvent.getEventId(), String.valueOf(jobEvent.getJobId()),
			        jobEvent.getRegistryHandler(), jobEvent.getCmd(), jobEvent.getHandlerType(),
			        jobEvent.getParams().split(System.lineSeparator()));
			return new Resp<>(event);
		}
		return new Resp<>(null);
	}
	
	/**
	 * 客户端处理 event 结果上报
	 * @author suoyao
	 * @date 下午4:57:50
	 * @param agentId
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/handler-event-report/{eventId}", method = RequestMethod.POST)
	public Resp<String> handlerEventReport(@PathVariable(value = "eventId", required = false) String eventId,
	        Resp<String> resp) {
		jobEventService.handlerJobEventReport(eventId, resp);
		return Resp.SUCCESS;
	}

}
