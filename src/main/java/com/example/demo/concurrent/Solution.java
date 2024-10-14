package com.example.demo.concurrent;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Ejemplo donde ejecuta dos tareas de manera concurrente.
 */
class SafeCracking {
	public void rob(IActions actions) throws InterruptedException, ExecutionException {
		try {
			String passCodeOfDoor = actions.unlockTheDoor();
			String safeDepositBoxNumber;
			String safeDepositBoxPin;

			FutureTask<String> futureTask1 = new FutureTask<>(new Callable<String>() {
				@Override
				public String call() throws InterruptedException, ExecutionException {
					return actions.hackSecretPin(passCodeOfDoor);
				}
			});

			FutureTask<String> futureTask2 = new FutureTask<>(new Callable<String>() {
				@Override
				public String call() throws InterruptedException, ExecutionException {
					return actions.figureOutSafeDepositBoxNumber(passCodeOfDoor);
				}
			});

			Thread hilo1 = new Thread(futureTask1);
			Thread hilo2 = new Thread(futureTask2);

			hilo1.start();
			hilo2.start();

			safeDepositBoxPin = futureTask1.get(); 
			safeDepositBoxNumber = futureTask2.get();
			actions.openSafeDepositLock(safeDepositBoxNumber, safeDepositBoxPin);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

	}
}

public class Solution {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		IActions actions = Actions.actions;
		new SafeCracking().rob(actions);
		System.out.println(actions.getRobberyVerdict());
	}
}

class Actions implements IActions {
	public static IActions actions = new Actions();

	private String passcodeOfDoor;
	private String safetyBoxNumber;
	private String pinOfSafetyBox;

	private String passedPasscodeOfDoor;
	private String passedSafetyBoxNumber;
	private String passedPinOfSafetyBox;

	private Actions() {
		this.passcodeOfDoor = randomStringGenerator(10);
		this.safetyBoxNumber = randomStringGenerator(10);
		this.pinOfSafetyBox = randomStringGenerator(10);
	}

	private String randomStringGenerator(int targetStringLength) {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return generatedString;
	}

	public boolean getRobberyVerdict() {
		return (passcodeOfDoor == passedPasscodeOfDoor) && (pinOfSafetyBox == passedPinOfSafetyBox)
				&& (safetyBoxNumber == passedSafetyBoxNumber);
	}

	public String unlockTheDoor() {
		return this.passcodeOfDoor;
	}

	public String hackSecretPin(final String passcodeOfDoor) throws InterruptedException, ExecutionException {
		this.passedPasscodeOfDoor = passcodeOfDoor;
		Thread.sleep(2000);
		return this.pinOfSafetyBox;
	}

	public String figureOutSafeDepositBoxNumber(final String passcodeOfDoor)
			throws InterruptedException, ExecutionException {
		Thread.sleep(2000);
		return this.safetyBoxNumber;
	}

	public void openSafeDepositLock(final String safetyDespositBoxNumber, final String safetyDepositBoxPin) {
		this.passedSafetyBoxNumber = safetyDespositBoxNumber;
		this.passedPinOfSafetyBox = safetyDepositBoxPin;
	}
}

interface IActions {
	String unlockTheDoor();

	String hackSecretPin(final String passCodeOfDoor) throws InterruptedException, ExecutionException;

	String figureOutSafeDepositBoxNumber(final String passCodeOfDoor) throws InterruptedException, ExecutionException;

	void openSafeDepositLock(final String safedepositBoxNumber, final String safeDepositBoxPin);

	boolean getRobberyVerdict();
}