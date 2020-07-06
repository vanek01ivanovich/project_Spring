package com.example.project.service.implementation;

import com.example.project.entity.DestinationProperty;
import com.example.project.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PageServiceImpl {

    /**
     * Method makes pagination
     * @param page needed for getting current page
     * @param pageable needed for pagination
     * @param list needed for making pagination in list object
     * @return Page objetc
     */
    public Page<?> pagination(String page, Pageable pageable, List<?> list){
        Page<?> pages;
        if (page != null){
            page = page.replaceAll("\\?.*","");
            int start = pageable.getPageSize()*Integer.parseInt(page);
            int end = Math.min((start + pageable.getPageSize()), list.size());
            pages = new PageImpl<>(list.subList(start,end), pageable, list.size());
        }else if(list.size() <= pageable.getPageSize()){
            pages = new PageImpl<>(list.subList(0, Math.min(list.size(),pageable.getPageSize())), pageable, list.size());
        }else{
            pages = new PageImpl<>(list.subList(0, Math.min(list.size(),pageable.getPageSize())), pageable, list.size());
        }
        return pages;
    }

}
