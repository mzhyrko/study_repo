#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include "list.h"

int main() {
    char command[20];
    bool cont = true;
    int r, index, value;
    list l = malloc(sizeof(list_t));
    l->first = l->last = NULL;
    l->length = 0;

    printf("Commands: pop, push, append, print, length, get, put, insert, delete, clean, insert");

    while (cont) {
        printf("Command: ");
        scanf("%s", command);

        if (!strcmp(command, "pop")) {
            if (!is_empty(l)) {
                r = pop(l);
                printf("Result: %d\n", r);
            } else {
                printf("Error - stack is empty!\n");
            }
        }
        else if (!strcmp(command, "push")) {
            printf("Value: ");
            scanf("%d", &r);
            push(l, r);
            printf("Result: OK\n");
        }
        else if (!strcmp(command, "append")) {
            printf("Value: ");
            scanf("%d", &r);
            append(l, r);
            printf("Result: OK\n");
        }
        else if (!strcmp(command, "print")) {
            printf("Result: ");
            print(l);
        }
        else if (!strcmp(command, "length")) {
            printf("Result: %d\n", length(l));
        }
        else if (!strcmp(command, "get")) {
            printf("Index: ");
            scanf("%d", &index);
            if (index < 1 || index > l->length) {
                printf("Error - bad index!\n");
            } else {
                r = get(l, index);
                printf("Result: %d\n", r);
            }
        }
        else if (!strcmp(command, "put")) {
            printf("Index: ");
            scanf("%d", &index);
            printf("Value: ");
            scanf("%d", &value);
            if (index < 1 || index > l->length) {
                printf("Error - bad index!\n");
            } else {
                put(l, index, value);
                printf("Result: OK\n");
            }
        }
        else if (!strcmp(command, "insert")) {
            printf("Index: ");
            scanf("%d", &index);
            printf("Value: ");
            scanf("%d", &value);
            if (index < 1 || index > l->length + 1) {
                printf("Error - bad index!\n");
            } else {
                insert(l, index, value);
                printf("Result: OK\n");
            }
        }
        else if (!strcmp(command, "delete")) {
            printf("Index: ");
            scanf("%d", &index);
            if (index < 1 || index > l->length) {
                printf("Error - bad index!\n");
            } else {
                delete(l, index);
                printf("Result: OK\n");
            }
        }
        else if (!strcmp(command, "clean")) {
            clean(l);
            printf("Result: OK\n");
        }
        else if (!strcmp(command, "exit")) {
            cont = false;
        }
        else {
            printf("Unknown command!\n");
        }
    }

    clean(l);
    free(l);

    return 0;
}

