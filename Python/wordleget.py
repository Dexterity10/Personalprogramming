import requests
from datetime import datetime, timedelta

def fetch_wordle_data(date):
    base_url = "https://www.nytimes.com/svc/wordle/v2/"
    url = base_url + date.strftime('%Y-%m-%d') + ".json"
    response = requests.get(url)
    if response.status_code == 200:
        solution = response.json()["solution"]
        print_date = response.json()["print_date"]
        return f"{solution},{print_date}"
    else:
        return None

def main():
    solutions = []
    today = datetime.now()
    while True:
        print(f"Fetching data for {today.strftime('%Y-%m-%d')}...", end="", flush=True)
        data = fetch_wordle_data(today)
        if data:
            solutions.append(data)
            today -= timedelta(days=1)
            print(f"done. {data}")
        else:
            print("failed.")
            break
    
    # Sort the solutions alphabetically
    solutions.sort()
    return solutions

if __name__ == "__main__":
    ordered_solutions = main()
    with open("wordle_solutions.csv", "w") as f:
        f.write("solution,print_date\n")
        for solution in ordered_solutions:
            f.write(solution + "\n")
            print(solution)