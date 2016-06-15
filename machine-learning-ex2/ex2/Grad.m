data = load('ex2data1.txt');
X = data(:, [1, 2]); y = data(:, 3);


%  Setup the data matrix appropriately, and add ones for the intercept term
[m, n] = size(X);
U = [ones(m, 1) X];
%plotData(X, y)
[X,mu,sigma] = featureNormalize(X);
% Add intercept term to x and X_test
X = [ones(m, 1) X];
mean(X(:,2))
% Initialize fitting parameters
initial_theta = zeros(n + 1, 1);

alpha = 0.1;
num_iters = 4000;
[theta1,JHist]=GradientDescent(X, y, initial_theta, alpha, num_iters);
k = 1:4000;
figure(10);
plot(k,JHist);
xlabel("Number of Iterations");
ylabel("Cost J");

p = predict(theta1,X);
o = ([1,45,85]-[0 mu]) ./ [1 sigma]
prob = double(sigmoid(o * theta1))

plotDecisionBoundary(10.*theta1, 10.*X, y);

fprintf('Train Accuracy: %f\n', mean(double(p == y)) * 100);
fprintf('\nProgram paused. Press enter to continue.\n');
pause;