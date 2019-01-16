package com.disnodeteam.dogecv.scoring;

import org.opencv.core.Mat;

/**
 * Created by Victo on 9/10/2018.
 */

public abstract class DogeCVScorer {
    public abstract double calculateScore(Mat input);
}
