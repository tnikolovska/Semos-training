package com.project.holidaymanagementproject.util;

import com.project.holidaymanagementproject.model.Person;
import org.springframework.ui.Model;
import java.util.List;
import java.util.stream.Collectors;

public class PaginationUtil {

    public static <E> List<E> getSublist(int pageNo, int pageSize, List<E> list) {
        int pagesToSkip = pageNo * pageSize;
        return list.stream().skip(pagesToSkip).limit(pageSize).collect(Collectors.toList());
    }

    public static int getTotalPages(int pageSize, int totalElements) {
        int pagesNo = totalElements / pageSize;
        return  (totalElements % pageSize) == 0 ? pagesNo : pagesNo + 1;
    }



}
