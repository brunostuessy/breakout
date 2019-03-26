[![GitHub Release](https://img.shields.io/github/release/brunostuessy/breakout.svg)](https://github.com/brunostuessy/breakout/releases) 
[![Build Status](https://travis-ci.com/brunostuessy/breakout.svg?branch=master)](https://travis-ci.com/brunostuessy/breakout)
</br>
[![lgtm code quality](https://img.shields.io/lgtm/grade/java/g/brunostuessy/breakout.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/brunostuessy/breakout/context:java)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c309b085ff9949fa8403078c87d0138f)](https://www.codacy.com/app/brunostuessy/breakout?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=brunostuessy/breakout&amp;utm_campaign=Badge_Grade)
</br>
[![codecov.io](http://codecov.io/github/brunostuessy/breakout/coverage.svg?branch=master)](https://codecov.io/gh/brunostuessy/breakout/branch/master)
[![Coverage Status](https://coveralls.io/repos/github/brunostuessy/breakout/badge.svg?branch=master&service=github&checksum)](https://coveralls.io/github/brunostuessy/breakout?branch=master)
</br>
[![Known Vulnerabilities](https://snyk.io/test/github/brunostuessy/breakout/badge.svg?targetFile=pom.xml)](https://snyk.io/test/github/brunostuessy/breakout?targetFile=pom.xml)

# BreakOut Strategy

## Functionality

The strategy goes long when close price crosses below BB and exits when crosses above MA,
and vice versa goes short when close price crosses above BB and exits when crosses below MA.

![Chart](chart.png)

## Technology

### Language

The code is based on Java 8 and leverages Reactor framework for running and testing the strategy.

## Quality

### Code

Code quality is measured automatically by two analyzers and targets zero issues.

### Test

Code coverage by tests is measured automatically by two analyzers and targets >=95%.

### Security

Library quality is measured automatically by one analyzer and targets zero issues.