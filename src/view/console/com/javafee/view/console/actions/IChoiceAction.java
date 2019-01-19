package com.javafee.view.console.actions;

public interface IChoiceAction<T> {

	public void displayDescription(String description);

	public T create();

	public T delete();

	public void printAll(Iterable<T> iterable);

}
