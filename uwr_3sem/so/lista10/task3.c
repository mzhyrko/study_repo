#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

const int n = 50;
int tally = 0;  // shared int tally = 0;

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;

void* total(void* arg) {
    for (int count = 1; count <= n; count++) {
        //pthread_mutex_lock(&mutex);
        
        int temp = tally;     // load 
        temp = temp + 1;      // add 
        //for (volatile int delay = 0; delay < 50000; delay++);
        tally = temp;         // store
        
        //pthread_mutex_unlock(&mutex);
    }
    return NULL;
}

int main(int argc, char* argv[]) {
    if (argc != 2) {
        printf("use: %s 2\n", argv[0]);
        return 1;
    }

    int k = atoi(argv[1]);
    if (k < 1) k = 1;

    pthread_t* threads = malloc(k * sizeof(pthread_t));

    printf("calc: %d\n", k * n);

    // parbegin(total(), total(), ..., total()); – sym
    for (int i = 0; i < k; i++) {
        pthread_create(&threads[i], NULL, total, NULL);
    }

    for (int i = 0; i < k; i++) {
        pthread_join(threads[i], NULL);
    }

    printf("real: %d\n", tally);
    free(threads);
    return 0;
}
