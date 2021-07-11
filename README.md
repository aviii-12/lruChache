# lruChache
LRU cache in java

Used deque to store key value pair
Used hashmap to find whether a key is present
deque was implimented so that we can get their next and prev and remove an element from middle in O(1). (when an element is used we need to remove it from its position and put into the front of queue).
