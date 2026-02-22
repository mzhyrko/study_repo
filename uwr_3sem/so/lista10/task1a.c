#include <stdio.h>
#include <pthread.h>

static int counter = 0;  

int increase_counter() {
    int temp = counter;   
    temp = temp + 1;      
    counter = temp;      
    return counter;
}

void* thread_func(void* arg) {
    for (int i = 0; i < 100000; i++) {
        increase_counter();
    }
    return NULL;
}

int main() {
    pthread_t t1, t2;

    pthread_create(&t1, NULL, thread_func, NULL);
    pthread_create(&t2, NULL, thread_func, NULL);

    pthread_join(t1, NULL);
    pthread_join(t2, NULL);

    printf("Oczekiwana: 200000\n");
    printf("Rzeczywist: %d\n", counter);

    return 0;
}
