import random

"""
This is a homemade cipher that popped into my head on 8/24/2026.
I was thinking about how absurdly complex the enigma code was,
and how it could be simplified while still having the capabilities of more complex encryption.
"""

KEY = 7  # pick a number!
RULE = [
    -KEY,
    KEY,
    KEY**2,
]  # Not necessary to change these, but you can if you'd like. All additions to RULE must always result in an integer
ASCII_START = 32  # starts with " " at ASCII 32 and ends at "z" at 126
ASCII_COUNT = 95  # total ASCII characters available (between 32 and 126)


def shift(char, value):
    output = ord(char) - ASCII_START
    output = (output + value) % ASCII_COUNT
    return chr(output + ASCII_START)


def encodeString(text=str):
    output = ""
    text = list(text)  # turn plaintext into a list of strings
    for i in range(len(text)):
        output += shift(text[i], RULE[i % len(RULE)])
    return output


def decodeString(text=str):
    output = ""
    text = list(text)  # turn plaintext into a list of strings
    for i in range(len(text)):
        output += shift(text[i], -RULE[i % len(RULE)])
    return output


def main():
    plaintext = [
        "Hello World! This is a message encoded with a homemade cipher.",
        "Fun fact: Whale blowholes (something) actually have two holes in them. [apples are fun] They look very similar to nose holes in a human skull!",
        "Four score and 30-some-odd years ago, I was not born. I am not 110! (and I'm especially not 1.5E178 years old, which is 110 factorial...)",
    ]

    for x in plaintext:
        enc = encodeString(x)
        print("text   :", x)
        print("encoded:", enc)
        print("decoded:", decodeString(enc))
        print("--------")


main()
