function [J, grad] = costFunctionReg(theta, X, y, lambda)
%COSTFUNCTIONREG Compute cost and gradient for logistic regression with regularization
%   J = COSTFUNCTIONREG(theta, X, y, lambda) computes the cost of using
%   theta as the parameter for regularized logistic regression and the
%   gradient of the cost w.r.t. to the parameters. 

% Initialize some useful values
m = length(y); % number of training examples

% You need to return the following variables correctly 
J = 0;
grad = zeros(size(theta));

% ====================== YOUR CODE HERE ======================
% Instructions: Compute the cost of a particular choice of theta.
%               You should set J to the cost.
%               Compute the partial derivatives and set grad to the partial
%               derivatives of the cost w.r.t. each parameter in theta


sum1 = 0;
h = y.*log(sigmoid(X*theta))+((1.-y).*log(1.-sigmoid(X*theta)));
h = -1.*h;
for i = 1:m,
 sum1 = sum1 + h(i);
end
J = sum1/m;
sum2 = 0;
for i = 2:length(theta),
  sum2 = sum2 + theta(i)^2;
end
sum2 = sum2*lambda/2;
sum2 = sum2/m;
J = J + sum2 ; 
grad(1) =1/m* sum((sigmoid(X*theta)-y).*X(:,1));
for i = 2:size(grad),
  grad(i) =1/m* sum((sigmoid(X*theta)-y).*X(:,i)) + (lambda/m*theta(i));
end
% =============================================================

end
