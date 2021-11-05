import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author Ariya Nazari Foroshani
 * @version 1.0
 * @userid YOUR USER ID HERE: aforoshani3
 * @GTID YOUR GT ID HERE 903627990
 *
 * Collaborators: None
 *
 * Resources: Basic google search on DoublyLinkedList and how it is structured
 */
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index provided is out of bounds");
        }
        if (data == null) {
            throw new IllegalArgumentException("the data given is null");
        }
        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data, null, null);

        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else if (index == 0) {
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
        } else if (index == size) {
            newNode.setPrevious(tail);
            tail.setNext(newNode);
            tail = newNode;
        } else {
            DoublyLinkedListNode<T> beforeIndex = getNode(index - 1);
            newNode.setNext(beforeIndex.getNext());
            newNode.setPrevious(beforeIndex);
            beforeIndex.getNext().setPrevious(newNode);
            beforeIndex.setNext(newNode);
        }
        size++;
    }

    /**
     *
     * @param index takes in the index at which the targeted node is located in.
     * @return returns the node at the given location indicated by index.
     */
    private DoublyLinkedListNode<T> getNode(int index) {
        DoublyLinkedListNode<T> current = head;
        //it checks to see wether it is more efficient to go through the front or the back of the list
        if ((size % 2 == 0 && index >= (size / 2)) || (size % 2 != 0 && index >= Math.ceil(size / 2))) {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.getPrevious();
            }
        } else {
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
        }
        return current;
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        addAtIndex(size, data);
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */

    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index is not within the bounds of the list");
        }
        T toReturn = null;
        if (size == 1) {
            toReturn = head.getData();
            head = null;
            tail = null;
        } else if (index == 0) {
            toReturn = head.getData();
            head = head.getNext();
            head.setPrevious(null);
        } else if (index == size - 1) {
            toReturn = tail.getData();
            tail = tail.getPrevious();
            tail.setNext(null);
        } else {
            DoublyLinkedListNode<T> beforeIndex = getNode(index - 1);
            toReturn = beforeIndex.getNext().getData();
            beforeIndex.setNext(beforeIndex.getNext().getNext());
            beforeIndex.getNext().setPrevious(beforeIndex);
        }
        size--;
        return toReturn;
    }



    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */

    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty");
        }
        return removeAtIndex(0);
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */

    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty");
        }
        return removeAtIndex(size - 1);
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index is out of the range for the list");
        }
        return getNode(index).getData();
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */

    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("the data given is null");
        }
        T toReturn = null;
        DoublyLinkedListNode<T> current = tail;
        boolean notFound = true;
        for (int i = size - 1; i >= 0; i--) {
            if (current.getData().equals(data)) {
                toReturn = removeAtIndex(i);
                notFound = false;
                break;
            }
            current = current.getPrevious();
        }
        if (notFound) {
            throw new NoSuchElementException("The data entered was not found");
        }
        return toReturn;
    }


    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */

    public Object[] toArray() {
        T[] array = (T[]) new Object[size];
        DoublyLinkedListNode<T> current = head;
        for (int i = 0; i < size; i++) {
            array[i] = current.getData();
            current = current.getNext();
        }
        return array;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
