#!/user/local/bin/python3.11
import os
import subprocess
import socket


def printHelp():
    print(
        """
1. display the [default] gateway
2. test [local] connectivity
3. test [remote] connectivity
4 test [dns] resolution
5. exit/quit the script """
    )


def defaultGateway():
    result = subprocess.run(
        ["ip" "r" "|" "grep" "^default" "|" "awk" "{print $3}"],
        capture_output=True,
        text=True,
    )
    print("default via", result.stdout)


def localConnect(host="127.0.0.1", port=80):
    try:
        with socket.create_connection((host, port), timeout=5):
            print(f"Connection to {host} on port {port} is successful")
    except OSError as e:
        print(e)


def remoteConnect():
    print("remote")


def testDNS():
    print("dns")


def main():
    while True:
        inpt = input("Command: ")
        match (inpt.lower()):
            case "help":
                printHelp()
            case "1" | "default":
                defaultGateway()
            case "2" | "local":
                localConnect()
            case "3" | "remote":
                remoteConnect()
            case "4" | "dns":
                testDNS()
            case "exit" | "quit":
                break
            case _:
                print("unknown command")


if __name__ == "__main__":
    main()
