key = "11110011"
string = "01010111 01101001 01101011 01101001"


def decode(string, key):
    out = ""
    string = string.replace(" ", "")
    for i in range(len(string)):
        out += str(int(string[i]) ^ int(key[i % len(key)]))
    return out


def encode(string, key):
    out = ""
    string = string.replace(" ", "")
    for i in range(len(string)):
        out += str(int(string[i]) ^ int(key[i % len(key)]))
    return out


def main():
    output = encode(string, key)
    print("encode: ", output)
    output = decode(output, key)
    print("decode: ", output)


main()
