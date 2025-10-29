#include <signal.h>
#include <stdio.h>
#include <sys/ioctl.h>

void sigwinch_handler(int sig) {
    struct winsize ws;
    ioctl(0, TIOCGWINSZ, &ws);
    printf("\n--- NEW SIZE: %dx%d ---\n", ws.ws_row, ws.ws_col);
}

int main() {
    signal(SIGWINCH, sigwinch_handler);
    printf("CHANGE WINDOW SIZE...\n");
    while (1) pause();
}
