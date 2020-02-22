### Challenge:
Write an algorithm to maximize both customer satisfaction and theater utilization in a movie theatre, fulfilling all/maximum possible 
reservation requests. 

### Assumptions:
* Assume seating arrangement of 10 rows x 20 seats.
* All seats requested in a single reservation must lie on the same row as much as possible.
* In cases when all requested seats cannot lie together in the same row, the seats will be split into multiple rows where priority will be given to fit maximum people together in each of those rows.
* In cases when the requested seats are greater than the available seats, the entire reservation will be skipped and the next reservation will be tried to be fulfilled.
    
### Running:
* The program takes commandline arguments:
    1. `assign`: 
        In this case, another commandline argument with the input filepath should also be provided. The program will then try to assign seats.
    2. `test`:
        This case will run all the unit test cases written.

### Notes:
* sample input files are given in *resources/*, which also includes test files. 

