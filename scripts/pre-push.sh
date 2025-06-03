#!/bin/sh

# Function to check if pushing to protected branch
check_protected_branch() {
    CURRENT_BRANCH=$(git rev-parse --abbrev-ref HEAD)
    PROTECTED_BRANCHES="main master"

    for branch in $PROTECTED_BRANCHES; do
        if [ "$CURRENT_BRANCH" = "$branch" ]; then
            echo "❌ Direct pushes to '$CURRENT_BRANCH' branch are not allowed. Please create a pull request instead."
            exit 1
        fi
    done
}

# Function to run ktlint checks
run_ktlint_checks() {
    echo "Running ktlint format..."
    ./gradlew ktlintFormat > /tmp/ktlint-result
    KTLINT_EXIT_CODE=$?

    if [ ${KTLINT_EXIT_CODE} -ne 0 ]; then
        cat /tmp/ktlint-result
        rm /tmp/ktlint-result
        echo "💥 Formatting issues found."
        exit ${KTLINT_EXIT_CODE}
    else
        rm /tmp/ktlint-result
        echo "🎉 Code formatted successfully."
    fi
}

# Function to run dependency guard
run_dependency_guard() {
    echo "Generating dependency baseline..."
    ./gradlew dependencyGuardBaseline > /tmp/dependency-result
    KT_EXIT_CODE=$?

    if [ ${KT_EXIT_CODE} -ne 0 ]; then
        cat /tmp/dependency-result
        rm /tmp/dependency-result
        echo "💥 Error generating dependency baseline."
        exit ${KT_EXIT_CODE}
    else
        rm /tmp/dependency-result
        echo "🎉 Dependency baseline generated successfully."
    fi
}

# Function to run Detekt checks
run_detekt_checks() {
    echo "Running Detekt..."
    ./gradlew detekt > /tmp/detekt-result
    DETEKT_EXIT_CODE=$?

    if [ ${DETEKT_EXIT_CODE} -ne 0 ]; then
        cat /tmp/detekt-result
        rm /tmp/detekt-result
        echo "💥 Issues found by Detekt."
        exit ${DETEKT_EXIT_CODE}
    else
        rm /tmp/detekt-result
        echo "🎉 Detekt analysis completed successfully."
    fi
}

# Function to print success message
print_success_message() {
    GIT_USERNAME=$(git config user.name)
    echo "✅ Code is ready to be pushed, $GIT_USERNAME!"
}

# Main script execution
check_protected_branch
run_ktlint_checks
run_detekt_checks
run_dependency_guard
print_success_message

exit 0
