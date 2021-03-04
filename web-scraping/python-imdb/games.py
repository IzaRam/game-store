import requests
from bs4 import BeautifulSoup
import pandas as pd

r = requests.get("https://www.imdb.com/search/title/?sort=user_rating,desc&title_type=game")
soup = BeautifulSoup(r.text, 'html.parser')

# Find All Names
elem_name = soup.findAll("h3", {'class': 'lister-item-header'})
list_name = []
for item in elem_name:
    names = item.find("a").contents
    for name in names:
        list_name.append(name)
print(list_name)

# Find All Dates
elem_date = soup.findAll("h3", {'class': 'lister-item-header'})
list_date = []
for item in elem_date:
    date = item.find("span", {'class': 'lister-item-year'}).contents[0][1:5]
    list_date.append(date)
print(list_date)

# Find All Descriptions
elem_desc = soup.findAll("div", {'class': 'lister-item-content'})
list_desc = []
for item in elem_desc:
    desc = item.findAll('p', {'class': 'text-muted'})[1].contents[0]
    desc = str.strip(desc)
    list_desc.append(desc)
print(list_desc)

# Find All Images URLs
elem_img = soup.findAll('div', {'class': 'lister-item-image'})
list_image = []
for item in elem_img:
    url = item.find('img')['loadlate']
    list_image.append(url)
print(list_image)


# Create CSV
df = pd.DataFrame({'Name': list_name,
                   'Year': list_date,
                   'Description': list_desc,
                   'Image': list_image
                   })
df.to_csv('games.csv', index=False)
