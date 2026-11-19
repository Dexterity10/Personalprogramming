key = "key"
debug = True
debugInput = "rlf{olssvdvysk}"  # returns key{helloworld}
debugOutput = "key{helloworld}"
if debug:
    input = debugInput
else:
    input = ""


def solve(input):
    for shift in range(26):
        out = ""
        for char in input:
            temp = char
            if char.isalpha():
                temp = ord(char) + shift
                if temp > ord("z"):
                    temp -= 26
                temp = chr(temp)
            # print("temp ", temp)
            out += temp
            # print("out: ", out)
        if debug and out == debugOutput:
            print(out)
            break


def main():
    solve(input)


main()
