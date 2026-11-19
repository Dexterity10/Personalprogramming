import string
import hashlib
from collections import deque


flagStart = "key" + "{"
flagEnd = "}"

debug = False
if debug:
    fullFlag = flagStart + "abc" + flagEnd  # for debug purposes
    hash = hashlib.sha256(fullFlag.encode()).hexdigest()
else:
    fullFlag = "unknown"
    hash = "d72d998eda0358a3f73f92dc767999dfb9be7c8ded367bd91f176eb76625859a"
print("flag: ", fullFlag, "\nhash: ", hash)  # Prints the Flag and Hash

printable = string.printable.strip()


def dehash_bfs(target_hash):
    queue = deque([""])  # Start with empty string

    while queue:
        current = queue.popleft()
        for char in printable:
            candidate = current + char
            full_flag = flagStart + candidate + flagEnd
            candidate_hash = hashlib.sha256(full_flag.encode()).hexdigest()

            if candidate_hash == target_hash:
                print("Match found!")
                print("Plaintext:", candidate)
                print("Full flag:", full_flag)
                return

            # Avoid unnecessary long candidates
            if len(candidate) <= 10:  # Adjust limit based on expected length
                queue.append(candidate)


def main():
    # dehash(hash)
    dehash_bfs(hash)


main()
