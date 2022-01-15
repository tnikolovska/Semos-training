package com.project.holidaymanagementproject.controller;

import com.project.holidaymanagementproject.model.OffDay;
import com.project.holidaymanagementproject.model.Person;
import com.project.holidaymanagementproject.model.PersonOffDay;
import com.project.holidaymanagementproject.model.Status;
import com.project.holidaymanagementproject.service.OffDayService;
import com.project.holidaymanagementproject.service.PersonService;
import com.project.holidaymanagementproject.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@Controller
@RequestMapping("/offDay")
public class OffDayController {
	@Autowired
	private OffDayService offDayService;
	@Autowired
	private PersonService personService;
	@GetMapping("/registerOffDay")
	public String registerView(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		OffDay newOffDayRequest = new OffDay();
		Person person = personService.getPersonByEmail(email);
		model.addAttribute("person",person);
		model.addAttribute("newOffDayRequest",newOffDayRequest);
		return "create_offday";
	}
	@RequestMapping(value="/register",method = RequestMethod.POST)
	public String registerOffDay(@RequestParam int idPerson,
								 @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
								 @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate endDate,
								 @RequestParam String description
	)
	{

		offDayService.registerOffDay(startDate, endDate, description, idPerson);
		return "redirect:/personOffDay/getOffDayRequests";
	}


	@GetMapping("/editOffDayView/{id}")
	public String getOffDayEditView(@PathVariable(value = "id")int id, Model model) {
		OffDay offDay = offDayService.getOffDayById(id);
		model.addAttribute("currentOffDay", offDay);
		return "offday_edit";
	}

	@RequestMapping(value="/update",method = RequestMethod.POST)
	public String updateOffDay(@ModelAttribute OffDay offDay) {
		offDayService.updateOffDay(offDay);
		return "redirect:/personOffDay/getOffDayRequests";
	}

	//ADMIN
	@RequestMapping(value="/validate/{id}",method = RequestMethod.GET)
	public String validation(@PathVariable(value = "id") int offDayId) {
		OffDay offDay = offDayService.getOffDayById(offDayId);
		offDayService.validation(offDay);
		return "redirect:/offDay/offDayRequests";
	}

	//ADMIN
	@GetMapping("/offDayRequests")
	public String allRequestsView(Model model) {
		return getAllRequests(0,"","","","",null,model);
	}

	//ADMIN
	@GetMapping("/offDayRequests/filtered")
	public String allRequestsFilteredView(Model model,
										  @RequestParam(required = false,name = "referenceId") String referenceId,
										  @RequestParam(required = false,name = "nameOrSurname") String nameOrSurname,
										  @RequestParam(required = false,name = "startDate") String startDate,
										  @RequestParam(required = false,name = "endDate") String endDate,
										  @RequestParam(required = false,name = "status") String status) {
		return getAllRequests(0,referenceId, nameOrSurname, startDate, endDate, status, model);
	}
	//ADMIN
	@GetMapping("/offDayRequests/page/{pageNo}/{referenceId}/{nameOrSurname}/{startDate}/{endDate}/{status}")
	public String getAllRequests(@PathVariable(value = "pageNo")int pageNo,
								 @PathVariable(value = "referenceId") String referenceId,
								 @PathVariable(value = "nameOrSurname") String nameOrSurname,
								 @PathVariable(value = "startDate") String startDate,
								 @PathVariable(value = "endDate") String endDate,
								 @PathVariable(value ="status") String status,
								 Model model) {

		referenceId = referenceId.isEmpty() || referenceId.equals("blank") ? null : referenceId;
		nameOrSurname = nameOrSurname.isBlank() || nameOrSurname.equals("blank") ? null : nameOrSurname;

		LocalDate startDateConverted = startDate.isBlank() || startDate.equals("blank") ? null : LocalDate.parse(startDate);
		LocalDate endDateConverted = endDate.isBlank() || endDate.equals("blank") ? null : LocalDate.parse(endDate);
		Status statusConverted = status==null || status.equals("blank") ? null : Status.valueOf(status);
		List<PersonOffDay> offDays = offDayService.offDayFilter(referenceId,nameOrSurname, startDateConverted, endDateConverted,statusConverted);
		int pageSize = 10;
		if (offDays != null && !offDays.isEmpty()) {
			List<PersonOffDay> offDaysSublist = PaginationUtil.getSublist(pageNo, pageSize, offDays);
			model.addAttribute("offDayList", offDaysSublist);
		}
		int totalOffDays = offDays != null ? offDays.size() : 0;
		int totalPages = PaginationUtil.getTotalPages(pageSize, pageNo);
		String redirectView ="/offDay/offDayRequests";
		if (pageNo > totalPages) return "redirect:" + redirectView;
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", PaginationUtil.getTotalPages(pageSize, totalOffDays));
		model.addAttribute("redirectView", redirectView);
		model.addAttribute("referenceId", referenceId==null ? "blank" : referenceId);
		model.addAttribute("nameOrSurname", nameOrSurname==null ? "blank" : nameOrSurname);
		model.addAttribute("startDate", startDateConverted==null ? "blank" : startDateConverted);
		model.addAttribute("endDate", endDateConverted==null ? "blank" : endDateConverted);
		model.addAttribute("status", status==null ? "blank" : status);

		return "offday_requests_view";
	}


	@RequestMapping(method = RequestMethod.GET,value="/search")
	public List<PersonOffDay> offDayFilter(@RequestParam(required = false) String referenceId, @RequestParam(required = false) String nameSurname, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate, @RequestParam(required = false) Status status ) {

		return offDayService.offDayFilter(referenceId, nameSurname, date,endDate, status);
	}

}
