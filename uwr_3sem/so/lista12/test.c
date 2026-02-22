#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>

__thread long myid;

static char **strtab;

void *thread(void *vargp) {
    myid = *(long *)vargp;

    static int cnt = 0;
    int tmp = cnt;             
    sleep(0.01);            
    cnt = tmp + 1;            
    
    printf("[%ld]: %s (cnt=%d)\n", myid, strtab[myid], cnt);

    return NULL;
}

int main(int argc, char *argv[]) {
    int n = argc - 1;         
    pthread_t *threads = malloc(n * sizeof(pthread_t));

    strtab = argv;

    for (int i = 0; i < n; i++) {
        int *id_ptr = malloc(sizeof(int));
        *id_ptr = i;        
        pthread_create(&threads[i], NULL, thread, id_ptr);
    }

    for (int i = 0; i < n; i++) {
        pthread_join(threads[i], NULL);
    }
    return 0;
}
