games = ["pydle", "series"]


def main():
    global games
    userInpt = input(
        f"Which game would you like to play?\n{''.join(str(i+1) + ": " + str(p) +"\n"for i,p in zip(range(0,len(games)),games))}"
    )
    for game in games:
        if userInpt in game:
            return game
            break
        else:
            return False


main()
