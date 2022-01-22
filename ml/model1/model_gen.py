## importing
import nltk
import pandas as pd
import os
import pickle

from sklearn.feature_extraction.text import TfidfVectorizer
from nltk.corpus import stopwords
from sklearn.naive_bayes import MultinomialNB
from  sklearn.metrics  import accuracy_score

# importing the data
folder = r'.\aclImdb'
labels = {'pos': 1, 'neg': 0}

df = pd.DataFrame()
for f in ('test', 'train'):
    for l in ('pos', 'neg'):
        path = os.path.join(folder, f, l)
        for file in os.listdir (path) :
            with open(os.path.join(path, file),'r', encoding='utf-8') as infile:
                txt = infile.read()
            df = df.append([[txt, labels[l]]],ignore_index=True)
df.columns = ['review', 'sentiment']


# setting up the necessary tools for preprocessing
tokenizer = nltk.RegexpTokenizer("\w+")
stop_words = set(stopwords.words('english'))


# preprocessing
for str in df:
    # tokenizing while removing punctuation
    str = str.lower()
    valid_tokens = tokenizer.tokenize(str)

    # removing useless words
    valid_tokens = [word for word in valid_tokens if not word in stop_words]


## training the ml model
review_train = df.loc[:24999, 'review'].values
rating_train = df.loc[:24999, 'sentiment'].values
review_test = df.loc[25000:, 'review'].values
rating_test = df.loc[25000:, 'sentiment'].values


vectorizer = TfidfVectorizer(stop_words='english')
train_vectors = vectorizer.fit_transform(review_train)
test_vectors = vectorizer.transform(review_test)
print(train_vectors.shape, test_vectors.shape)
clf = MultinomialNB().fit(train_vectors, rating_train)


predicted = clf.predict(test_vectors)
print(accuracy_score(rating_test,predicted))

pickle.dump(clf, open('temp.txt', 'wb'))