# Online Python - IDE, Editor, Compiler, Interpreter
import random, person

susList = []
everybody = []
culprit = 0
toDie = culprit
clueKey = [0, 0, 0]


def clues(new=0):
    if new == 1:
        clueKey[random.randint(0, 2)] = 1
    print(
        f"The culprit has {getattr(everybody[culprit], "__hair__") if clueKey[0] == 1 else ""}"
        f"{", " if clueKey[0] == 1 and clueKey[1] == 1 else ""}"  # if first and second clues are given
        f"{getattr(everybody[culprit], "__eyes__") if clueKey[1] == 1 else ""}"
        f"{", " if clueKey[1] == 1 and clueKey[2] == 1 else ""}"  # if second and third clues are given
        f"{", " if clueKey[0]==1 and clueKey[2]==1 and clueKey[1] != 1 else ""}"  # if first and third clues are given, but not second
        f"{getattr(everybody[culprit], "__skin__") if clueKey[2] == 1 else ""}."
    )


def print_list():
    global susList
    for i in range(len(susList)):
        print(str(i) + ": " + str(susList[i]))


def print_everybody():
    global everybody
    for i in range(len(everybody)):
        print(str(i) + ": " + str(everybody[i]))


def initiate(count):
    global culprit
    for i in range(count):
        everybody.append(person.Suspect())
        susList.append(person.Suspect())
    culprit = random.randint(0, len(everybody) - 1)


def day():
    global susList
    global toDie
    while toDie == culprit:
        toDie = random.randint(0, len(everybody) - 1)
    print(getattr(everybody[toDie], "__name__") + " died!\n")
    everybody[toDie].set_dead
    susList.pop(toDie)
    clues(1)
    print_list()


def main():
    global culprit
    global susList
    initiate(10)
    day()
    print(
        "commands:\
    \nLIST for people; specify all for everyone, not just suspects\
    \nINFO for clues\
    \nRM to remove someone from your suspects list\
    \nADD to add someone to your suspects list\
    \nGUESS to accuse someone.\
    \nQUIT to give up\
    \ncommands are not case sensitive"
    )
    while True:
        cmd = input("> ")
        cmd = cmd.lower()
        cmd = cmd.split()
        cmd.append(None)
        if cmd[0] == "list":
            if cmd[1] == "all":
                print_everybody()
            else:
                print_list()
        if cmd[0] == "info":
            clues()
        if cmd[0] == "quit":
            break
        if cmd[0] == "guess":
            if cmd[1] != None:
                if cmd[1] == str(culprit):
                    print("You solved the case!")
                    break
                else:
                    print("Incorrect!")
                    if len(everybody) >= 1:
                        day()
                    else:
                        print("Everybody died. You lost!")
                        break
            else:
                print("No index detected.")
        if cmd[0] == "rm":
            if cmd[1] != None:
                print("removed", susList.pop(int(cmd[1])))
                print_list()
            else:
                print("No index detected.")
        if cmd[0] == "add":
            if cmd[1] != None:
                print("added", susList.append(everybody(int([cmd[1]]))))
                print_list()


main()
