# AQR Backend

## Description

This is the Java backend of the Air Quality Radar, which is responsible for providing the API for the frontend to retrieve radar data points at a certain time and location.

## Building

To build, run `mvn compile`.  To build and run unit tests, use `mvn test`.  If you want to use an IDE, the project has been set up to work with [IntelliJ IDEA](https://www.jetbrains.com/idea/).

## Contribution Guidelines

To work on a new feature, checkout the master branch and run `git branch CRSID/FEATURE_NAME`, for example if your CRSID was `ab123` and you added some fields to the radar data point class, you could do `git branch ab123/radar-point-additions`.  

Once you've finished the changes you wanted to make, push your changes to the server (`git push origin ab123/radar-point-additions`) and [open a merge request](https://gitlab.com/air-quality-radar/AQR-Backend/merge_requests).
