package com.LearnLC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumOfBurgers {
    public static List<Integer> numOfBurgers(int tomatoSlices, int cheeseSlices) {
        List<Integer> used = new ArrayList<>(2);
        int useBig = 0,useSmall = 0;
        while(tomatoSlices > 0 && cheeseSlices > 0){
            if(tomatoSlices > cheeseSlices * 2){
                int temp = (tomatoSlices - cheeseSlices * 2) / 4 + 1;
                tomatoSlices -= 4 * temp;
                cheeseSlices -= 1 * temp;
                useBig += temp;

            }
            else if (tomatoSlices == cheeseSlices * 2){
                useSmall += cheeseSlices;
                tomatoSlices -= cheeseSlices * 2;
                cheeseSlices -= cheeseSlices;
            }
            else break;
        }
        if(tomatoSlices == 0 && cheeseSlices == 0){
            used.add(useBig);
            used.add(useSmall);
        }
        return used;
    }
    public static void main(String[] args){

        System.out.println(numOfBurgers(17,4));
    }
}
