text1 = "#love #tumblr #memes #quotes #funnytexts #texting #funny #tweets #textpost \
#texture #frasi #tweegram #tweet #tweetext #tweetexts #texgram #tweedride #instapage \
#tweembler #frasier #frasitumblr #tweeter #tweemblers #tweeters #frasista #textmessage \
#frasistas #bhfyp"

text2 = """A hashtag is a metadata tag that is prefaced by the pound sign or hash symbol,\
 # (not to be confused with the pound currency sign). Hashtags are used on microblogging\
 and photo-sharing services such as Twitter, Instagram and WeChat as a form of user-generated \
tagging that enables cross-referencing of content; that is, sharing a topic or theme.\
 For example, a search within Instagram for the hashtag #bluesky returns all posts that \
have been tagged with that hashtag. After the initial hash symbol, a hashtag may include \
letters, digits, and underscores. The use of hashtags was first proposed by American blogger, product consultant and speaker \
Chris Messina in a 2007 tweet. Messina made no attempt to patent the use because he felt \
"they were born of the internet, and owned by no one". In 2013, Twitter purportedly told \
the Wall Street Journal that "these things are for nerds" and their use "wouldn't be adopted \
widely."] By the end of the decade, though, hashtags were entrenched in the culture \
of the platform, and they soon emerged across Instagram, Facebook, and YouTube. In June \
2014, hashtag was added to the Oxford English Dictionary, as "a word or phrase with the \
symbol # in front of it, used on social media websites and apps so that you can search \
for all messages with the same subject"."""

text1List = text1.split(" ")
text1List.sort()
print(text1List)

text1 = " ".join(text1List)

text1 = text1.replace("#", " ")
print(text1)

text1List2 = text1.split("  ")
print(text1List2)

text1Dict = dict()

for element in text1List2:
    text1Dict[element] = len(element)

print('{0:15s} \t {1:10s}'.format("Word", "Characters"))

for element in text1Dict.keys():
    print('{0:15s} \t {1:10d}'.format(element, text1Dict[element]))

text1 = text1.replace(" ", "")
print(text1)

text2 = text2.replace(",", ";")
print(text2)

text2List = text2.split(" ")


count = 0
for element in text2List:
    if element.lower() == "in":
        count += 1

print(count)

text1Set = set(text1)
text2Set = set(text2)

print(text1Set)
print(text2Set)

text2ListSentences = text2.split(". ")


text2ListSentences[0] = text2ListSentences[0].capitalize()
text2ListSentences[1] = text2ListSentences[1].upper()
text2ListSentences[2] = text2ListSentences[2].lower()
text2ListSentences[3] = text2ListSentences[3].title()

newSentence = ""
vowels = ['a', 'e', 'o', 'u', 'i', 'y']

for i in range(0, len(text2ListSentences[4])):
    if text2ListSentences[4][i] in vowels:
        newSentence += text2ListSentences[4][i].swapcase()
    else:
        newSentence += text2ListSentences[4][i]

text2ListSentences[4] = newSentence

myString = ". ".join(text2ListSentences[0:5])

print(myString)
