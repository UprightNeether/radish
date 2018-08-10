package org.sam.shen.scheduing.controller.portal;

import java.util.List;

import org.sam.shen.scheduing.entity.Agent;
import org.sam.shen.scheduing.entity.AgentGroup;
import org.sam.shen.scheduing.entity.RespPager;
import org.sam.shen.scheduing.service.AgentService;
import org.sam.shen.scheduing.vo.AgentEditVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;

/**
 * @author suoyao
 * @date 2018年8月10日 上午11:22:38
  * 
 */
@Controller
@RequestMapping(value="portal")
public class AgentController {
	
	@Autowired
	private AgentService agentService;
	
	/**
	 * @author suoyao
	 * @date 上午11:22:35
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "agent", method = RequestMethod.GET)
	public ModelAndView toAgentPage(ModelAndView model) {
		model.setViewName("frame/agent/agent");
		return model;
	}
	
	/**
	 *  Agent 查询分页 JSON数据集合
	 * @author suoyao
	 * @date 上午11:22:12
	 * @param page
	 * @param limit
	 * @param agentName
	 * @return
	 */
	@RequestMapping(value = "agent/json", method = RequestMethod.GET)
	@ResponseBody
	public RespPager<Page<Agent>> queryAgentForJson(@RequestParam("page") Integer page,
	        @RequestParam("limit") Integer limit,
	        @RequestParam(value = "agentName", required = false, defaultValue = "") String agentName) {
		if(null == page) {
			page = 1;
		}
		if(null == limit) {
			limit = 10;
		}
		Page<Agent> pager = agentService.queryAgentForPager(page, limit, agentName);
		return new RespPager<>(pager.getPageSize(), pager.getTotal(), pager);
	}
	
	/**
	 * Agent 编辑
	 * @author suoyao
	 * @date 上午11:21:39
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = {"agent-edit/", "agent-edit/{id}"}, method = RequestMethod.GET)
	public ModelAndView agentEdit(ModelAndView model, @PathVariable(value = "id", required = false) Long id) {
		if(null != id) {
			AgentEditVo agentView = agentService.agentEditView(id);
			model.addObject("agentView", agentView);
		} else {
			model.addObject("agentView", new AgentEditVo());
		}
		model.setViewName("frame/agent/agent_edit");
		return model;
	}
	
	@RequestMapping(value = "agent-edit-save", method = RequestMethod.POST)
	public String agentEditSave(ModelAndView model, @ModelAttribute Agent agent, 
	        @RequestParam("handlers") List<String> handlers) {
		return "redirect:/portal/agent-edit/";
	}

	@RequestMapping(value = "agent-group", method = RequestMethod.GET)
	public ModelAndView queryAgentGroup(ModelAndView model) {
		
		model.setViewName("frame/agent/agent_group");
		return model;
	}
	
	@RequestMapping(value = "agent-group-add", method = RequestMethod.GET)
	public ModelAndView agentGroupAdd(ModelAndView model) {
		
		model.setViewName("frame/agent/agent_group_add");
		return model;
	} 
	
	@RequestMapping(value = "agent-group-save", method = RequestMethod.POST)
	public ModelAndView agentGroupSave(ModelAndView model, AgentGroup agentGroup) {
		
		return null;
	}
	
}