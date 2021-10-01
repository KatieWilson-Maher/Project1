import kotlin.math.ln
import kotlin.math.max

// Process is a class to model a process
class Process(){
    // procTime represents the time it takes for a process to run
    val procTime = expDist(10.0)
    // arrivalDiff represents time between the arrival of the previous process and the arrival of this process
    val arrivalDiff = expDist(10.0)
    // procArrival represents the time at which the process arrives (value will be replaced later)
    var procArrival = 0.0
    // procBegin represents the time at which the process begins (value will be replaced later)
    var procBegin = 0.0
    // procEnd represents the time at which the process ends (value will be replaced later)
    var procEnd = 0.0
}

// expDist is a function that returns a random number from an exponential distribution
fun expDist(specifiedMean: Double): Double{
    val rng = kotlin.random.Random(System.nanoTime())
    val makeExpRNG = { mean:Double -> { n:Int -> -mean * ln(rng.nextDouble()) }}
    val expRNG: (Int) -> Double = makeExpRNG(specifiedMean)
    return expRNG(1)
}

// listOfProcs is a function that creates and returns a list of processes given an integer for the size
fun listOfProcs(size: Int): MutableList<Process>{
    // create empty mutable list
    var procList = mutableListOf<Process>()
    // iterate through the list
    for(ind in 0..size){
        // create an instance of the class Process
        val proc = Process()
        // add the new process to the list
        procList.add(proc)
    }
    // return the list of processes
    return procList
}

// arrivalTimes is a function that populates the procArrival attribute for each process in a list of processes
fun arrivalTimes(listProcs: MutableList<Process>){
    // set the arrival time of the first process to 0
    listProcs[0].procArrival = 0.0
    // iterate through the rest of the processes in the list
    for(item in 1 until (listProcs.size)) {
        // set the arrival time of the current process to the previous processes' arrival time plus the time between arrivals
        listProcs[item].procArrival = listProcs[item-1].procArrival + listProcs[item].arrivalDiff
    }
}

// beginAndEndTimes is a function that populates the begin and end times of each process in a list of processes
fun beginAndEndTimes(listProcs: MutableList<Process>){
    // set the begin time of the first process to 0
    listProcs[0].procBegin = 0.0
    // set the end time of the first process to the beginning time plus the process time
    listProcs[0].procEnd = listProcs[0].procBegin + listProcs[0].procTime
    // iterate through the rest of the processes in the list
    for(item in 1 until (listProcs.size)) {
        // set the beginning time of the current process to the max of the current processes' arrival time and the previous
        // processes' end time
        listProcs[item].procBegin = max(listProcs[item].procArrival, listProcs[item-1].procEnd)
        // set the end time of the current process to the beginning time plus the process time
        listProcs[item].procEnd = listProcs[item].procBegin + listProcs[item].procTime
    }
}

fun main(args: Array<String>) {
    // create a list of procedures of size 10
    val listProcs = listOfProcs(10)
    // populate the arrival times of the processes
    arrivalTimes(listProcs)
    // populate the beginning and end times of the processes
    beginAndEndTimes(listProcs)
}