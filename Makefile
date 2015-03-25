JAVAC=javac

updatePrice:
	$(JAVAC) updatePrice.java

Analyze:
	$(JAVAC) Analyze.java

.PHONY: update
update:
	java updatePrice 556 554 564 562 561 563 560 565 440 383 1521 1515 1777 379 453

.PHONY: all
all: updatePrice Analyze

.PHONY: clean
clean:
	rm -f *.txt *.class
