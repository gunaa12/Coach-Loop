# imports
import nltk
import pickle
import joblib
import re
from nltk.corpus import stopwords
from nltk.stem.porter import PorterStemmer
from sklearn.feature_extraction.text import CountVectorizer

def main():
    # read the review from the review file
    file_input = input()
    review = file_input.read()
    file_input.close()

    # initializing
    nltk.download('stopwords')
    ps = PorterStemmer()

    # preprocessing
    all_stopwords = stopwords.words('english')
    all_stopwords.remove('not')

    review = re.sub('[^a-zA-Z]', ' ', review)
    review = review.lower()
    review = review.split()
    review = [ps.stem(word) for word in review if not word in set(all_stopwords)]
    review = ' '.join(review)

    # importing model and predicting
    cv = CountVectorizer(decode_error="replace", vocabulary=pickle.load(open('bag_of_words.pkl', "rb")))
    cv = pickle.load(open('bag_of_words.pkl', "rb"))
    review = [review]
    X_fresh = cv.transform(review).toarray()

    classifier = joblib.load('./classifier_sentiment_model')
    y_pred = classifier.predict(X_fresh)

    return y_pred

if __name__ == "__main__":
    main()