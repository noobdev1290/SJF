import java.util.Arrays;
import java.util.Scanner;

class Process implements Comparable<Process> {
    int id;
    int arrivalTime;
    int burstTime;

    public Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }

    public int getId() {
        return id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }
    @Override
    public int compareTo(Process other) {

        if (this.arrivalTime == 0 && other.arrivalTime != 0) {
            return -1; // Current process has arrival time of 0, so it should come before the other process
        } else if (this.arrivalTime != 0 && other.arrivalTime == 0) {
            return 1; // Other process has arrival time of 0, so it should come before the current process
        } else if (this.arrivalTime == other.arrivalTime) {
            return this.burstTime - other.burstTime;
        }
        return this.burstTime - other.burstTime;
    }

    @Override
    public String toString() {
        return "Process " + id + ": Arrival Time = " + arrivalTime + " Burst Time = " + burstTime;
    }
}

public class ShortestJobFirst {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();
        int processesCompleted = 0;

        Process[] processes = new Process[n];

        // Input the processes
        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details for Process " + (i + 1) + ":");
            System.out.print("Arrival Time: ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Burst Time: ");
            int burstTime = scanner.nextInt();
            processes[i] = new Process(i + 1, arrivalTime, burstTime);
            processesCompleted++;
            System.out.println("Process " + processes[i].id + " completed.");
        }

        // Sort the processes by burst time (shortest first)
        Arrays.sort(processes);

        int[] completionTime = new int[n];
        int[] turnaroundTime = new int[n];
        int[] waitingTime = new int[n];
        int totalBurstTime = 0;
        int totalTurnAroundTime = 0;
        int totalWaitingTime = 0;

        // Calculate completion time for each process
        completionTime[0] = processes[0].burstTime;
        for (int i = 1; i < n; i++) {
            completionTime[i] = completionTime[i - 1] + processes[i].burstTime;
        }

        // Calculate turnaround time for each process
        for (int i = 0; i < n; i++) {
            turnaroundTime[i] = completionTime[i] - processes[i].arrivalTime;
            totalTurnAroundTime += turnaroundTime[i];
        }

        // Calculate waiting time for each process
        for (int i = 0; i < n; i++) {
            waitingTime[i] = completionTime[i] - processes[i].arrivalTime - processes[i].burstTime;
            totalWaitingTime += waitingTime[i];
        }

        // Display the processes and their end, turnaround, and waiting times
        System.out.println("\nProcess \t\t Arrival Time \t\t Burst Time \t\t End Time \t\t Turnaround Time \t\t Waiting Time");
        for (int i = 0; i < n; i++) {
            System.out.println(processes[i].getId() + "\t\t\t\t\t" + processes[i].getArrivalTime() +"\t\t\t\t\t" + processes[i].getBurstTime() + "\t\t\t\t\t" + completionTime[i] + "\t\t\t\t\t" + turnaroundTime[i] + "\t\t\t\t\t" + waitingTime[i]);
            //Tracker to keep track when a process is completed
        }

        // Calculate and display cpu utilization
        for (int i = 0; i < n; i++){
            totalBurstTime += processes[i].burstTime;
        }

        double cpuUtilization = (double) totalBurstTime/completionTime[n - 1] * 100;
        System.out.println("\nCPU Utilization: " + cpuUtilization + "%");


        // Calculate and display average turnaround time
        double averageTurnAroundTime = (double) totalTurnAroundTime / n;
        System.out.println("Average Turnaround Time: " + averageTurnAroundTime);

        // Calculate and display average waiting time
        double averageWaitingTime = (double) totalWaitingTime / n;
        System.out.println("Average Waiting Time: " + averageWaitingTime);

        // Display the number of completed processes
        System.out.println("Total Completed Processes: " + processesCompleted);

        // Calculate and display throughput
        double throughput = (double) n / completionTime[n - 1];
        System.out.println("Throughput: " + throughput + " processes per time unit");

        scanner.close();
    }
}