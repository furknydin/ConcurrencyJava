# Java Concurrency Notes

## What is the thread-safe?
Thread safe comes from correctness.<br>
*A class is thread-safe if it behaves correctly when accessed from multiple
threads, regardless of the scheduling or interleaving of the execution of
those threads by the runtime environment, and with no additional synchronization 
or other coordination on the part of the calling code. No set of operations 
performed sequentially or concurrently on instances of a thread-safe class can cause an instance to be in 
an invalid state.Thread-safe classes encapsulate any needed synchronization so that
clients need not provide their own.*<br>
*Stateless objects are always thread-safe.*

## Intrinsic Lock
Intrinsic locks play a role in both aspects of synchronization: enforcing exclusive access to an object's state and 
establishing happens-before relationships that are essential to visibility. As long as a thread owns an intrinsic lock, no other thread can acquire the same lock. The other thread will block when it attempts to acquire the lock.


## Concurrency Models
* ## Parallel Workers
In the parallel workers concurrency model a delegator distributes the incoming jobs to different workers.
Each worker completes the full job. The workers work in parallel, running in different threads, and 
possibly on different CPUs.


## Resources
* Brian Goetz. Java Concurrency in Practice. Massachusetts: Addison-Wesley, 2006
