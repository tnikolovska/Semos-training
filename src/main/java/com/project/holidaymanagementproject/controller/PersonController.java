package com.project.holidaymanagementproject.controller;

import com.project.holidaymanagementproject.exception.NoFreeSpaceException;
import com.project.holidaymanagementproject.model.Person;
import com.project.holidaymanagementproject.model.TypeOfUser;
import com.project.holidaymanagementproject.service.PersonService;
import com.project.holidaymanagementproject.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/person")
@PreAuthorize("hasAuthority('ADMIN')")
public class PersonController {

    @Autowired
    PersonService service;

    @GetMapping("/registerPersonView")
    public String getRegisterPage() {
        return "register_person";
    }

    @PostMapping("/registerPerson")
    public String createPerson(@RequestParam String firstName,
                               @RequestParam String lastName,
                               @RequestParam String email,
                               @RequestParam String typeOfUser,
                               @RequestParam String activeStatus) throws NoFreeSpaceException {
        service.savePerson(firstName, lastName, email, typeOfUser, activeStatus);
        return "redirect:/person/displayAll";
    }

    @GetMapping("/displayAll")
    public String viewAllMembers(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return paginationView(0, model, "all", "all");
    }

    @GetMapping("/displayAll/page/{pageNo}/{filterType}/{filterValue}")
    public String paginationView(@PathVariable(value = "pageNo") int pageNo,
                                 Model model,
                                 @PathVariable(value = "filterType") String filterType,
                                 @PathVariable(value = "filterValue") String filterValue) {
        List<Person> personList = service.returnFilteredList(filterType, filterValue).stream()
                .filter(person -> !person.getFirstName()
                        .equals("Admin"))
                .collect(Collectors.toList());
        String redirectView = "/person/displayAll";
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", PaginationUtil.getTotalPages(10, personList.size()));
        model.addAttribute("personList", PaginationUtil.getSublist(pageNo,10,personList));
        model.addAttribute("redirectView", redirectView);
        model.addAttribute("filterType", filterType);
        model.addAttribute("filterValue", filterValue);

        return "person_all";
    }


    @GetMapping("/displayAll/filtered")
    public String viewAllMembersFiltered(Model model,
                                         @RequestParam String filterType,
                                         @RequestParam String filterValue) {
        return paginationView(0, model, filterType, filterValue);
    }

    @GetMapping("/updatePersonView/{id}")
    public String getUpdatePage(@PathVariable(value = "id") int id, Model model) {
        Person currentPerson = service.getPersonByDBId(id);
        model.addAttribute("currentPerson", currentPerson);
        return "person_update";
    }

    @PostMapping("/updatePerson")
    public String updatePerson(@RequestParam String dbId,
                               @RequestParam String firstName,
                               @RequestParam String lastName,
                               @RequestParam String email,
                               @RequestParam String typeOfUser,
                               @RequestParam String activeStatus) {
        service.updatePerson(dbId, firstName, lastName, email, typeOfUser, activeStatus);
        return "redirect:/person/displayAll";
    }

    @GetMapping("/deletePerson/{id}")
    public String deletePerson(@PathVariable(value = "id") int id) {
        service.deletePerson(id);
        return "redirect:/person/displayAll";
    }

    @GetMapping("/peopleNames")
    public List<Person> getPeopleByName(@RequestParam String firstName) {
        return service.getPeopleByName(firstName);
    }

    @GetMapping("/peopleLastNames")
    public List<Person> getPeopleByLastName(@RequestParam String lastName) {
        return service.getPeopleByLastName(lastName);
    }

    @GetMapping("/userType")
    public List<Person> getPeopleByType(@RequestParam TypeOfUser type) {
        return service.getPeopleByType(type);
    }

    @GetMapping("/activePeople")
    public List<Person> getActivePeople() {
        return service.getActivePeople();
    }

    //THIS METHOD IS CREATED TO TEST getRefferenceID
    @GetMapping("/getRefferenceIds")
    public List<String> getRefferenceIds() {
        return service.getAllReferenceIds();
    }


}
