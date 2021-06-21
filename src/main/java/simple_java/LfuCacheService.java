package simple_java;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

public class LfuCacheService {

    private static final Logger logger = LogManager.getLogger(LfuCacheService.class);
    private Node head;
    private Node tail;
    private ConcurrentHashMap<Integer, Node> map;
    private final int capacity;
    private static int evictions;

    public LfuCacheService(int capacity) {
        this.capacity = capacity;
        this.map = new ConcurrentHashMap<>();
        BasicConfigurator.configure();
    }

    public String get(int key) {
        if (map.get(key) == null) {
            return null;
        }

        Node item = map.get(key);
        removeNode(item);
        item.frequency = item.frequency + 1;
        addNodeWithUpdatedFrequency(item);

        return item.value;
    }

    public void put(int key, String value) {
        long startTime = System.nanoTime();
        if (map.containsKey(key)) {
            Node item = map.get(key);
            item.value = value;
            item.frequency = item.frequency + 1;
            removeNode(item);
            addNodeWithUpdatedFrequency(item);
        } else {
            if (map.size() >= capacity) {
                map.remove(head.key);
                removeNode(head);
                evictions++;
                logger.info("Removed excessive node with key: " + head.key + ". Total evictions: " + evictions);
            }

            Node node = new Node(key, value, 1);
            addNodeWithUpdatedFrequency(node);
            map.put(key, node);
            long timeElapsed = System.nanoTime() - startTime;
            logger.info("Added new record with key: " + key + ". Time elapsed for operation in nanos: " + timeElapsed);
        }
    }

    private void removeNode(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
    }

    private void addNodeWithUpdatedFrequency(Node node) {
        if (tail != null && head != null) {
            Node temp = head;
            while (true) {
                if (temp.frequency > node.frequency) {
                    if (temp == head) {
                        node.next = temp;
                        temp.prev = node;
                        head = node;
                        break;
                    } else {
                        node.next = temp;
                        node.prev = temp.prev;
                        temp.prev.next = node;
                        break;
                    }
                } else {
                    temp = temp.next;
                    if (temp == null) {
                        tail.next = node;
                        node.prev = tail;
                        node.next = null;
                        tail = node;
                        break;
                    }
                }
            }
        } else {
            tail = node;
            head = tail;
        }
    }

    private static class Node {

        private final int key;
        private String value;
        private int frequency;
        private Node prev;
        private Node next;

        public Node(int key, String value, int frequency) {
            this.key = key;
            this.value = value;
            this.frequency = frequency;
        }
    }
}