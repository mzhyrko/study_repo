#include "csapp.h"  

#include <stdio.h>
#include "terminal.h"

int main() {
    int fd = tty_open();  // Otwórz terminal
    if (fd < 0) {
        return 1;
    }

    int x, y;
    while(true){
 
    tty_curpos(fd, &x, &y);
    printf("Aktualna pozycja kursora: (%d, %d)\n", x, y);
    
    }
    close(fd);
    return 0;
}
