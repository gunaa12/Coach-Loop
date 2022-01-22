# imports
import pandas as pd
import re
import nltk
import joblib
import pickle
from nltk.corpus import stopwords
from nltk.stem.porter import PorterStemmer
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.model_selection import train_test_split
from sklearn.naive_bayes import GaussianNB
from sklearn.metrics import confusion_matrix, accuracy_score

# initializations/dowloads
ps = PorterStemmer()
nltk.download('stopwords')
cv = CountVectorizer(max_features = 1420)

#gettig the dataset
dataset = pd.read_csv('./historic_data.tsv', delimiter = '\t', quoting = 3)

# preprocessing
all_stopwords = stopwords.words('english')
all_stopwords.remove('not')

corpus=[]
for i in range(0, 900):
  review = re.sub('[^a-zA-Z]', ' ', dataset['Review'][i])
  review = review.lower()
  review = review.split()
  review = [ps.stem(word) for word in review if not word in set(all_stopwords)]
  review = ' '.join(review)
  corpus.append(review)

# data transformation
X = cv.fit_transform(corpus).toarray()
y = dataset.iloc[:, -1].values
pickle.dump(cv, open('bag_of_words.pkl', "wb"))

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size = 0.1, random_state = 0)
classifier = GaussianNB()
classifier.fit(X_train, y_train)
joblib.dump(classifier, './classifier_sentiment_model')

# testing
y_pred = classifier.predict(X_test)
cm = confusion_matrix(y_test, y_pred)
print(accuracy_score(y_test, y_pred))