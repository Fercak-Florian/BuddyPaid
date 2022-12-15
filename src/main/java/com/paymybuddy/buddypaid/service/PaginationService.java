package com.paymybuddy.buddypaid.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.paymybuddy.buddypaid.workclasses.DisplayedOperation;

@Service
public class PaginationService {
	
	/*final*/ /*private List<DisplayedOperation> displayedOperations = new ArrayList<>();*/

    public Page<DisplayedOperation> findPaginated(Pageable pageable, List<DisplayedOperation> displayedOperations) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<DisplayedOperation> list;

        if (displayedOperations.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, displayedOperations.size());
            list = displayedOperations.subList(startItem, toIndex);
        }

        Page<DisplayedOperation> operationPage = new PageImpl<DisplayedOperation>(list, PageRequest.of(currentPage, pageSize), displayedOperations.size());

        return operationPage;
    }
}
