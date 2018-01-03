import sys
from bs4 import BeautifulSoup as bs
from nltk.corpus import stopwords
import re





def extract(qterm, doc):
    qterm = qterm.lower().strip().split()
    stops = set(stopwords.words("english"))
    filtered_words = [word for word in qterm if word not in stops]

    reg = ''
    for m in qterm:
        reg = reg + "/* " + m

    reg = reg + " /*"
    ret = ""
    # for doc in docs:          
    soup = bs(open(doc), "lxml")
    #l = soup.body.findAll(text=re.compile(reg, re.I))
    l=[]
    # l = soup.find(attrs={'itemprop':'description'}).findAll(text=re.compile(reg, re.I))
    # l += soup.find(attrs={'itemprop':'caption'}).findAll(text=re.compile(reg, re.I))
    # l=soup.find(itemprop='headline').findAll(text=re.compile(reg, re.I))
    #l=[soup.text]
    #l=[bs(open(doc).read(), "lxml").find("div", {"class": "main"}).getText(separator=" ")]+[bs(open(doc).read(), "lxml").find("title").getText(separator=" ")]   
    #l = soup.find("main",{'class':'main'}).findAll(text=re.compile(reg, re.I)) 
    l= soup.find("body").findAll(text=re.compile(reg, re.I))
    #l+=soup.find("title").findAll(text=re.compile(reg, re.I))
    # l+= soup.find(attrs={'class':'title'}).findAll(text=re.compile(reg, re.I))
    #if l=="":
    #    l= soup.body.findAll(text=re.compile(reg, re.I))  
    
    n = []
    m = re.compile("[A-Za-z0-9.,!/$]*")
    for x in l:

        temp = filter(m.match, x.strip())

        if (re.search("[\{\[_]", temp) == None):
            n.append(x)
    k=[]        
    if n==[]:
        for word in filtered_words:
            l = soup.body.find(text=re.compile("/* "+word+" /*", re.I))
            y = soup.find("title")
            if y!=None:
                k+=y.findAll(text=re.compile(word, re.I))
            if l!=None:    
                if re.search("[\{\[_]", filter(m.match,l)) == None:
                    n.append(l)
    n=n+k            
    s = ""
    for x in n:
        x=''.join(e for e in x if e.isalnum() or e==" " or e=="-")
        s = x.strip() + "........." + s

    if len(s) > 100:
        s= s[0:300]


    if s != '':
        for word in filtered_words:
            insensitive = re.compile(re.escape(word), re.IGNORECASE)
            s=insensitive.sub("<strong>" + word + "</strong>", s)

    return s

        

    
        
reload(sys)


sys.setdefaultencoding('utf-8')


syss = sys.argv[1].strip()
docs = sys.argv[2].strip()
#docs = docs.split(":")
#print docs;
#print unicode(syss);
s= extract(syss,docs)
print s
sys.stdout.flush()
