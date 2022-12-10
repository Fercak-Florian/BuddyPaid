package com.paymybuddy.buddypaid.workclasses;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class PartialDisplayOperation {
	
	public PartialDisplayOperation() {
	}
	
	public List<DisplayedOperation> calculateNumberOfOperationsPerPage(List<DisplayedOperation> displayedOperations, int page) {
		List<DisplayedOperation> partialDisplayedOperations = new ArrayList<>();
		int pageLenght = 3;
		int start = (page - 1) * pageLenght; 
		if(start >= displayedOperations.size()) {
			/*NE RIEN FAIRE*/
		} else {
			int end = start + (pageLenght - 1) ; 
			if(end < displayedOperations.size()) {
				
			} else {
				end = displayedOperations.size() - 1;
			}
			
			for(int i = start; i <= end ; i++) {
				partialDisplayedOperations.add(displayedOperations.get(i));
			}
		}
		return partialDisplayedOperations;
	}
}