import random, pyperclip
from datetime import datetime

types = ["arithmetic", "geometric"]


def setup():
    seed = "debug"  # datetime.now().strftime("%Y%m%d")
    # random.seed(seed)


def startgame():
    global types
    setup()
    type = random.randint(0, len(types) - 1)
    start = random.randint(-9, 10)

    match type:
        case 0:
            # arithmetic
            print("arith")
            step = random.randint(-99, 100)
            for i in range(0, 3):
                print(arithmetic(start, i, step), end=", ")
            answer = arithmetic(start, 4, step)

        case 1:
            # geometric
            print("geom")
            step = random.randint(-9, 10)
            for i in range(0, 3):
                print(geometric(start, i, step), end=", ")
            answer = geometric(start, 3, step)

        case _:
            print("error", types)
            answer = None
    print("...")
    guess = None
    toShare = ""
    while guess != answer:
        guess = int(input(""))
        print(answer)

        if guess != answer:
            if answer > guess:
                print("higher")
                toShare += "+"
                # print(toShare)
            if answer < guess:
                print("lower")
                toShare += "-"
                # print(toShare)
        elif guess == answer:
            print("correct!")
            toShare += "="
            toShare = str(len(toShare)) + ": " + toShare
            print(toShare)
            break


def arithmetic(start, stop, step):
    return start + step * stop


def geometric(start, stop, step):
    return start * step**stop


def main():
    startgame()


if __name__ == "__main__":
    main()
