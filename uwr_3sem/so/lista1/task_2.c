// zombie_demo.c
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main() {
    pid_t child = fork();
    if (child == 0) { // Child
        printf("Child (PID %d) exiting...\n", getpid());
        exit(0); // Becomes zombie until reaped
    } else { // Parent
        printf("Parent (PID %d) created child %d\n", getpid(), child);
        sleep(10); // Wait to observe zombie with 'ps -eo pid,ppid,stat,cmd'
        // Run 'ps' here to see Z state
        int status;
        waitpid(child, &status, 0); // Reap zombie
        printf("Reaped child with status %d\n", WEXITSTATUS(status));
    }
    return 0;
}
