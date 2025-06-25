#include <iostream>
#include <string>
#include <limits>

using namespace std;

template<typename T>
class Node {
public:
    T data;              
    Node<T>* left;       
    Node<T>* right;      

    Node(T value) : data(value), left(nullptr), right(nullptr) {}

    ~Node() {
        delete left;
        delete right;
    }
};

template<typename T>
class BinaryTree {
private:
    Node<T>* root;      

    void insertRecursive(Node<T>*& node, T value) {
        if (!node) {
            node = new Node<T>(value);
        }
        else if (value < node->data) {
            insertRecursive(node->left, value);
        }
        else if (value > node->data) {
            insertRecursive(node->right, value);
        }
    }

    bool searchRecursive(Node<T>* node, T value) const {
        if (!node) return false;
        if (value == node->data) return true;
        return (value < node->data) 
            ? searchRecursive(node->left, value)
            : searchRecursive(node->right, value);
    }

    Node<T>* findMinNode(Node<T>* node) const {
        while (node && node->left) node = node->left;
        return node;
    }

    Node<T>* deleteRecursive(Node<T>* node, T value) {
        if (!node) return nullptr;
        
        if (value < node->data) {
            node->left = deleteRecursive(node->left, value);
        }
        else if (value > node->data) {
            node->right = deleteRecursive(node->right, value);
        }
        else {
            if (!node->left && !node->right) {
                delete node;
                return nullptr;
            }
            else if (!node->left) {
                Node<T>* temp = node->right;
                node->right = nullptr;
                delete node;
                return temp;
            }
            else if (!node->right) {
                Node<T>* temp = node->left;
                node->left = nullptr;
                delete node;
                return temp;
            }
            else {
                Node<T>* minNode = findMinNode(node->right);
                node->data = minNode->data;
                node->right = deleteRecursive(node->right, minNode->data);
            }
        }
        return node;
    }

    void printTree(Node<T>* node, string indentation = "", 
                  bool isLeft = true, bool isInitialCall = true) const {
        if (node) {
            cout << indentation;
            
            if (!isInitialCall) {
                cout << (isLeft ? "|-->" : "`-->");
                indentation += (isLeft ? "|   " : "    ");
            }
            
            cout << node->data << std::endl;
            
            printTree(node->left, indentation, true, false);
            printTree(node->right, indentation, false, false);
        }
    }

public:
    BinaryTree() : root(nullptr) {}

    ~BinaryTree() { delete root; }

    void insert(T value) {
        insertRecursive(root, value);
    }

    void remove(T value) {
        root = deleteRecursive(root, value);
    }

    bool search(T value) const {
        return searchRecursive(root, value);
    }

    void print() const {
        if (!root) {
            cout << "(empty tree)" << endl;
        }
        else {
            printTree(root);
        }
    }
};

template<typename T>
void runTreeInterface() {
    BinaryTree<T> tree;
    int choice;
    T value;

    while (true) {
        cout << "\nBinary Tree Operations:\n";
        cout << "1. Insert\n2. Delete\n3. Search\n4. Print Tree\n5. Exit\n";
        cout << "Enter choice: ";
        cin >> choice;

        if (cin.fail()) {
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
            cout << "Error: Invalid input type\n";
            continue;
        }

        switch (choice) {
            case 1:  
                cout << "Enter value to insert: ";
                cin >> value;
                if (cin.fail()) {
                    cin.clear();
                    cin.ignore(numeric_limits<streamsize>::max(), '\n');
                    cout << "Error: Invalid value type\n";
                    break;
                }
                tree.insert(value);
                break;
                
            case 2:  
                cout << "Enter value to delete: ";
                cin >> value;
                if (cin.fail()) {
                    cin.clear();
                    cin.ignore(numeric_limits<streamsize>::max(), '\n');
                    cout << "Error: Invalid value type\n";
                    break;
                }
                tree.remove(value);
                break;
                
            case 3:  
                cout << "Enter value to search: ";
                cin >> value;
                if (cin.fail()) {
                    cin.clear();
                    cin.ignore(numeric_limits<streamsize>::max(), '\n');
                    cout << "Error: Invalid value type\n";
                    break;
                }
                cout << (tree.search(value) ? "Value found" : "Value not found") << endl;
                break;
                
            case 4:  
                cout << "\nBinary Tree Structure:\n";
                tree.print();
                break;
                
            case 5:  
                return;
                
            default:
                cout << "Invalid option\n";
        }
    }
}

int main() {
    int typeChoice;
    cout << "Select Tree Data Type:\n";
    cout << "1. Integer\n2. Double\n3. String\n";
    cout << "Enter choice: ";
    cin >> typeChoice;

    if (cin.fail()) {
        cout << "Error: Invalid type selection\n";
        return 1;
    }

    switch (typeChoice) {
        case 1:
            runTreeInterface<int>();
            break;
        case 2:
            runTreeInterface<double>();
            break;
        case 3:
            runTreeInterface<string>();
            break;
        default:
            cout << "Invalid selection\n";
    }
    
    return 0;
}
