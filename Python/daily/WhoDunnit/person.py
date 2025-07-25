import random

hairs = ["blonde", "brown", "black"]
eyes = ["blue", "brown", "green"]
skins = ["white", "brown", "black"]
titles = ["Mr.", "Ms.", "Mrs.", "Dr.", "Sir", "Madam"]
surnames = ["A", "B", "C", "D"]


class Suspect:
    __slots__ = ["__name__", "__hair__", "__eyes__", "__skin__"]

    def __init__(self):
        self.__name__ = (
            titles[random.randint(0, 5)] + " " + surnames[random.randint(0, 3)]
        )
        self.__hair__ = hairs[random.randint(0, 2)] + " hair"
        self.__eyes__ = eyes[random.randint(0, 2)] + " eyes"
        self.__skin__ = skins[random.randint(0, 2)] + " skin"

    def get_hair(self):
        return self.__hair__

    def get_eyes(self):
        return self.__eyes__

    def get_skin(self):
        return self.__skin__

    def set_dead(self):
        self.__name__ = "[Dead] " + self.__name__
        print(self.__name__, " is set to dead.")

    def __str__(self):
        return (
            str(self.__name__)
            + ": "
            + str(self.__hair__)
            + ", "
            + str(self.__eyes__)
            + ", "
            + str(self.__skin__)
        )
