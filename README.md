statistical-spam-detector
=========================

Run training with more memory:
```
java -Xmx2048M -Xms2048M Main train
```

Usage:
```
java Main [option] [args]
  options
			train	will run the training cycle and output to training.txt (default)
				    requires a spam.txt and nonspam.txt file
			test	will run the test cycle. Requires a training.txt file,
					a test_spam.txt file, and a test_nonspam.txt file
			words	will take in additional [args], and return probability of them being spam
```

example of running `words` option:
```
java Main words spam subject*spam hello world
```

output:
```
spam: 0.9487462167491202
subject*spam: 0.99
hello: 0.5019724590945313
world: 0.42850821809717426
```

Spam Archives: [here's one](http://untroubled.org/spam/ "spam archive from untroubled.org"), [here's another](http://www.dornbos.com/spam01.shtml "spam archive from dornbos.com")

We need to find Non-Spam archive(s).

We've split the work into 2 parts:

1. Parser/Tokenizer:
  - will be used for the training data and on the test data
2. Classifier
  - will use the file that the parser/tokenizer outputs to classify test data
  - will also use the parser/tokenizer to get tokens for the test emails

The file structure for training set will be as follows:
```
#of-spam-emails #of-non-spam-emails
word1 #freq-in-spam-emails #freq-in-non-spam-emails
word2 #freq-in-spam-emails #freq-in-non-spam-emails
.
.
.
wordn #freq-in-spam-emails #freq-in-non-spam-emails
```

The file structure for the spam.txt(training), nonspam.txt(training), test_spam.txt(test), test_nonspam.txt(test) will be as follows:
```
::new email::
::subject::
this is an example subject
::body::
this is
a multiline example body

::new email::
::subject::
.
.
.
```

