import requests
from bs4 import BeautifulSoup
import pandas as pd
import re

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
# elem_img = soup.findAll('div', {'class': 'lister-item-image'})
# list_image = []
# for item in elem_img:
#     url = item.find('img')['loadlate']
#     list_image.append(url)
# print(list_image)

# Find All Images URLs (Better resolution)
game_page_list = soup.findAll("h3", {'class': 'lister-item-header'})
image_url_list = []
for game_page in game_page_list:
    link = "https://imdb.com" + game_page.find("a")['href']
    r = requests.get(link)
    soup = BeautifulSoup(r.text, 'html.parser')
    result = soup.find("script", {'type': 'application/ld+json'})
    image_url = re.search(r'.+\"image\": \"(.+?)\",', result.string).group(1)
    image_url_list.append(image_url)
print(image_url_list)

# Create CSV
df = pd.DataFrame({'Name': list_name,
                   'Year': list_date,
                   'Description': list_desc,
                   'Image': image_url_list
                   })
df.to_csv('games.csv', index=False)
