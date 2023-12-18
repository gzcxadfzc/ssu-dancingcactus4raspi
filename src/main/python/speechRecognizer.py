import speech_recognition as sr
from nltk.tokenize import word_tokenize

def get_tokens():
    r = sr.Recognizer()
    with sr.Microphone() as source:
        print("Say something...")
        audio = r.listen(source)
        speech = r.recognize_google(audio, language="ko-KR")
        tokens = word_tokenize(speech)
        return tokens

while(True):
    try:
        print(get_tokens())
        print("recognized!")
    except sr.UnknownValueError:
        print("unknown")
