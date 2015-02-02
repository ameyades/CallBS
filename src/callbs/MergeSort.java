package callbs;


import java.util.*;

import callbs.SearchResultsActivity.String_Distance;

public class MergeSort {
	private ArrayList<String_Distance> array;
    private int[] tempMergArr;
    private int length;
 
  /*  public static void main(String a[]){
         
        int[] inputArr = {45,23,11,89,77,98,4,28,65,43};
        MyMergeSort mms = new MyMergeSort();
        mms.sort(inputArr);
        for(int i:inputArr){
            System.out.print(i);
            System.out.print(" ");
        }
    }*/
     
    public ArrayList<String_Distance> sort(ArrayList<String_Distance> inputArr) {
        this.array = inputArr;
        this.length = inputArr.size();
        this.tempMergArr = new int[length];
        doMergeSort(0, length - 1);
        return array;
    }
 
    private void doMergeSort(int lowerIndex, int higherIndex) {
         
        if (lowerIndex < higherIndex) {
            int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
            // Below step sorts the left side of the array
            doMergeSort(lowerIndex, middle);
            // Below step sorts the right side of the array
            doMergeSort(middle + 1, higherIndex);
            // Now merge both sides
            mergeParts(lowerIndex, middle, higherIndex);
        }
    }
 
    private void mergeParts(int lowerIndex, int middle, int higherIndex) {
 
        for (int i = lowerIndex; i <= higherIndex; i++) {
            tempMergArr[i] = array.get(i).getDistance();
        }
        int i = lowerIndex;
        int j = middle + 1;
        int k = lowerIndex;
        while (i <= middle && j <= higherIndex) {
            if (tempMergArr[i] <= tempMergArr[j]) {
                array.get(k).setDistance(tempMergArr[i]);
               // array.get(k).setMystring(array.get(i).getMystring());
                i++;
            } else {
                array.get(k).setDistance(tempMergArr[j]);
               // array.get(k).setMystring(array.get(j).getMystring());
                j++;
            }
            k++;
        }
        while (i <= middle) {
            array.get(k).setDistance(tempMergArr[i]);
          //  array.get(k).setMystring(array.get(i).getMystring());
            k++;
            i++;
        }
        
        
 
    }
}



